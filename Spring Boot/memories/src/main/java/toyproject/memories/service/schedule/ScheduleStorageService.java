package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.Importance;
import toyproject.memories.domain.schedule.ScheduleItem;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleItemCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageListDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;
import toyproject.memories.service.CreateMap;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleStorageService {

    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String,Object> createScheduleStorage(ScheduleStorageCreateDto scheduleStorageCreateDto, String username){

        User user = userRepository.findByName(username).orElse(null);

        if(user == null){
            CreateMap.failMap("잘못된 사용자 정보 입니다.");
        }

        ScheduleStorage scheduleStorage = new ScheduleStorage(
                scheduleStorageCreateDto.getTitle(),
                user);


        for(ScheduleItemCreateDto scheduleItem : scheduleStorageCreateDto.getScheduleItems()){
            System.out.println("스케쥴 시작일:" + scheduleItem.getStartDay());
            ScheduleItem scheduleItemEntity = ScheduleItem.builder()
                    .title(scheduleItem.getTitle())
                    .startTime(scheduleItem.startTimeParse())
                    .endTime(scheduleItem.endTimeParse())
                    .startDay(DayOfWeek.valueOf(scheduleItem.getStartDay()))
                    .endDay(DayOfWeek.valueOf(scheduleItem.getEndDay()))
                    .importance(Importance.valueOf(scheduleItem.getImportance()))
                    .build();

            scheduleStorage.addScheduleItem(scheduleItemEntity);
        }

        return CreateMap.successMap(scheduleStorageRepository.save(scheduleStorage));
    }

    public Map<String,Object> storageList(String username){
        User user = userRepository.findByName(username).orElse(null);
        if(user == null){
            return CreateMap.failMap("잘못된 사용자 정보 입니다.");
        }
        else {
            return CreateMap.successMap(scheduleStorageRepository.findAllByUser(user));
        }
    }

    public Map<String,Object> storageDetail(Long id, String username){
        User user = userRepository.findByName(username).orElse(null);

        ScheduleStorage storageReturn = scheduleStorageRepository.findOne(id,user)
                .stream().findAny().orElse(null);

        if(storageReturn != null)
            return CreateMap.successMap(new ScheduleStorageReturnDto(storageReturn));
        else
            return CreateMap.failMap("해당 storage 를 찾을 수 없습니다.");
    }

    public Map<String,Object> storageDelete(Long id, String username){
        User user = userRepository.findByName(username).orElse(null);

        List<ScheduleStorage> storageReturn = scheduleStorageRepository.findOne(id,user);

        if(!storageReturn.isEmpty()){
            return CreateMap.successMap(storageReturn.stream().findFirst()
                    .orElse(null)
                    .getId());
        }
        else
            return CreateMap.failMap("해당 storage 를 찾을 수 없습니다.");
    }
}
