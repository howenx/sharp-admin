package service;

import entity.Carriage;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public interface CarriageService {

    Integer insertCarriage(Carriage carriage);

    void updateCarriage(Carriage carriage);

    Carriage getCarriage(Long id);

    List<Carriage> getAllCarriage();

    List<Carriage> getModel();
}
