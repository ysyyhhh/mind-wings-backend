package cc.yysy.servicetimetable.Service;

import cc.yysy.utilscommon.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TimetableService {

    public Object insert(Map<String, Object> params, SysUser user);
}
