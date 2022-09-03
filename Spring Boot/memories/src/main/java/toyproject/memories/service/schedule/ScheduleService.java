package toyproject.memories.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.schedule.*;
import toyproject.memories.domain.user.User;
import toyproject.memories.repository.UserRepository;
import toyproject.memories.repository.schedule.ScheduleRepository;
import toyproject.memories.repository.schedule.ScheduleStorageRepository;
import toyproject.memories.repository.schedule.WeeklyScheduleCreateDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleStorageRepository scheduleStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Schedule> createWeeklySchedule(WeeklyScheduleCreateDto weeklyScheduleCreateDto, String username){
        User user = userRepository.findByName(username).orElse(null);

        ScheduleStorage scheduleStorage = scheduleStorageRepository.findOne(
                weeklyScheduleCreateDto.getStorage_id(),user
        ).stream().findAny().orElse(null);

        List<Schedule> schedules = new ArrayList<>();

        if(scheduleStorage != null){
            //방법1. 요일별로 아이템을 분류
            //시작일 부터 7번의 반복문
            //2중 반복문으로 내부에 endDay 까지 7일씩 더해가는 반복문
            //방법2. 아이템 반복문 돌면서
            //즉각즉각 요일 정보 얻어와서 7씩 더해서 넣기
            LocalDate start = LocalDate.parse(weeklyScheduleCreateDto.getStartDay());
            LocalDate end = LocalDate.parse(weeklyScheduleCreateDto.getEndDay());
            DayOfWeek startDay = start.getDayOfWeek();

            for(ScheduleItem item : scheduleStorage.getScheduleItems()){
                DayOfWeek itemStartDay = item.getStartDay();
                //(아이템의 요일) - (요청받은 시작일의 요일) 만큼 시작날짜에 더해서 요일을 맞춤
                LocalDate itemStartDate = start.plusDays((long)(itemStartDay.getValue()-startDay.getValue()) % 7);
                while(itemStartDate.isBefore(end)){
                    LocalDateTime itemStartDateTime = itemStartDate.atTime(item.getStartTime());
                    Schedule schedule = Schedule.builder()
                            .title(item.getTitle())
                            .startDateTime(itemStartDateTime)
                            .endDateTime(itemStartDateTime.plusHours(3L).plusMinutes(30L))
                            .user(user)
                            .importance(item.getImportance())
                            .scheduleStorage(scheduleStorage)
                            .build();
                    schedules.add(schedule);
                }
            }
        }
        else{

        }
        return scheduleRepository.saveSchedules(schedules);
    }

}
