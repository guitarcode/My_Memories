package toyproject.memories.service;

import java.util.HashMap;
import java.util.Map;

public class CreateMap {
    //수행에 성공했을 때 성공 알림과 함께 object를 반환
    public static Map<String, Object> successMap(Object object){
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("result","success");
        newMap.put("data",object);
        return newMap;
    }

    //수행에 실패했을 때 실패 알림과 함께 안내 메세지 반환
    public static Map<String, Object> failMap(String message){
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("result","fail");
        newMap.put("message",message);
        return newMap;
    }
}
