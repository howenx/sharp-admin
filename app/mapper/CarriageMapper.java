package mapper;

import entity.Carriage;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public interface CarriageMapper {

    Integer insertCarriage(Carriage carriage);

    void updateCarriage(Carriage carriage);

    String getModelName(String modelCode);

    List<Carriage> getAllCarriage();

    List<Carriage> getModel();
}
