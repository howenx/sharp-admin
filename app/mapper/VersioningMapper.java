package mapper;

import entity.VersionVo;

import java.util.List;

/**
 * 版本
 * Created by howen on 16/1/29.
 */
public interface VersioningMapper {

    Integer insertVersioning(VersionVo versionVo);

    List<VersionVo> getVersioning(VersionVo versionVo);

    Integer updateVersioning(VersionVo versionVo);
}
