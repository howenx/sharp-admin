package service;

import entity.statistics.SID;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SIDService {

    List<SID> getSIDBy(SID sid);

    List<SID> getAllSID();

}
