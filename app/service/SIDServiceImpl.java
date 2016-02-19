package service;

import entity.SID;
import mapper.SIDMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SIDServiceImpl implements SIDService {

    @Inject
    private SIDMapper sidMapper;

    @Override
    public List<SID> getSIDBy(SID sid) {
        return sidMapper.getSIDBy(sid);
    }

    @Override
    public List<SID> getAllSID() {
        return sidMapper.getAllSID();
    }

}
