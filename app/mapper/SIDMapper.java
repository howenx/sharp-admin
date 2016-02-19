package mapper;

import entity.SID;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SIDMapper {

    List<SID> getSIDBy(SID sid);

    List<SID> getAllSID();

}
