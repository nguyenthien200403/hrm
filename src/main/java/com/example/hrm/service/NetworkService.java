package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Network;
import com.example.hrm.repository.NetworkRepository;
import com.example.hrm.request.NetworkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NetworkService {
    private final NetworkRepository repository;

    public GeneralResponse<?> getAllNetwork(){
        List<Network> list = repository.findAll();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "Networks", list);
    }

    public GeneralResponse<?> create(NetworkRequest request){
        boolean exists = repository.existsBySsidAndMacRouter(request.getSsid(), request.getMacRouter());
        if (exists) {
            return new GeneralResponse<>( HttpStatus.CONFLICT.value(), "SSID and MAC address already existed.", null );
        }
        var network = Network.builder()
                .ssid(request.getSsid())
                .macRouter(request.getMacRouter())
                .ipPublic(request.getIpPublic())
                .build();
        repository.save(network);
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success create", null);
    }

    public GeneralResponse<?> update(Long id, NetworkRequest request){
        Optional<Network> findResult = repository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Network with Id: " + id, null);
        }

        boolean exists = repository.existsBySsidAndMacRouterAndIdNot( request.getSsid(), request.getMacRouter(), id );

        if(exists){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "SSID and MAC address already existed.", null);
        }

        Network network = findResult.get();
        network.setSsid(request.getSsid());
        network.setMacRouter(request.getMacRouter());
        network.setIpPublic(request.getIpPublic());

        repository.save(network);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Success update", null);
    }

    public GeneralResponse<?> delete(Long id){
        Optional<Network> findResult = repository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Network with Id: " + id, null);
        }

        Network network = findResult.get();
        repository.delete(network);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Success delete", null);
    }
}
