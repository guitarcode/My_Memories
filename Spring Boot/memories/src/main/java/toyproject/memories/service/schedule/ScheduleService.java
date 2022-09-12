package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.*;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleReturnDto;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;
import toyproject.memories.repository.schedule.WeeklyScheduleCreateDto;
import toyproject.memories.service.CreateMap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String,Object> createWeeklySchedule(WeeklyScheduleCreateDto weeklyScheduleCreateDto, String username){
        User user = userRepository.findByName(username).orElse(null);

        ScheduleStorage scheduleStorage = scheduleStorageRepository.findOne(
                weeklyScheduleCreateDto.getStorageId(),user
        ).stream().findAny().orElse(null);

        List<Schedule> schedules = new ArrayList<>();

        System.out.println(scheduleStorage.getId());
        if(scheduleStorage != null){
            //방법1. 요일별로 아이템을 분류
            //시작일 부터 7번의 반복문
            //2중 반복문으로 내부에 endDay 까지 7일씩 더해가는 반복문
            //방법2. 아이템 반복문 돌면서
            //즉각즉각 요일 정보 얻어와서 7씩 더해서 넣기
            LocalDate start = LocalDate.parse(weeklyScheduleCreateDto.getStartDay());
            LocalDateTime end = LocalDate.parse(weeklyScheduleCreateDto.getEndDay()).atTime(0,0);
            DayOfWeek startDay = start.getDayOfWeek();
            for(ScheduleItem item : scheduleStorage.getScheduleItems()){
                DayOfWeek itemStartDay = item.getStartDay();
                //(아이템의 요일) - (요청받은 시작일의 요일) 만큼 시작날짜에 더해서 요일을 맞춤
                LocalDateTime itemStartDateTime = start.plusDays((long)(itemStartDay.getValue()+7-startDay.getValue()) % 7)
                        .atTime(item.getStartTime());
                System.out.println("plusDay = " + (itemStartDay.getValue()+7-startDay.getValue()) % 7);
                LocalDateTime itemEndDateTime = itemStartDateTime.toLocalDate()
                        .plusDays(item.getEndDay().getValue() - start.getDayOfWeek().getValue())
                        .atTime(item.getEndTime());
                System.out.println("itemStartDateTime = " + itemStartDateTime);
                System.out.println("itemEndDateTime = " + itemEndDateTime);
                while(itemStartDateTime.isBefore(end)){
                    Schedule schedule = Schedule.builder()
                            .title(item.getTitle())
                            .subTitle(scheduleStorage.getTitle())
                            .startDateTime(itemStartDateTime)
                            .endDateTime(itemEndDateTime)
                            .user(user)
                            .importance(item.getImportance())
                            .scheduleStorage(scheduleStorage)
                            .build();
                    schedules.add(schedule);
                    itemStartDateTime = itemStartDateTime.plusDays(7L);
                    itemEndDateTime = itemEndDateTime.plusDays(7L);
                }
            }
            return CreateMap.successMap(scheduleRepository.saveSchedules(schedules));
        }
        else{
            return CreateMap.failMap("Bad Request");
        }
    }

    public List<ScheduleReturnDto> scheduleList(String username){
        User user = userRepository.findByName(username).orElse(null);

        return scheduleRepository.findAll(user).stream().map(ScheduleReturnDto::new).collect(Collectors.toList());
    }

}
