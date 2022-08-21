package toyproject.memories.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityServiceTest {
//    @Autowired ActivityRepository activityRepository;
//    @Autowired ActivityService activityService;
//    @Autowired TagRepository tagRepository;
//
//    @Test
//    public void 저장 () throws Exception {
//        //given
//        ActivityCreateDto activityCreateDto = new ActivityCreateDto();
//        activityCreateDto.setActivityContent("테스트 다이어리");
//        activityCreateDto.setTagContent("#여행 #경주 #유민");
//
//        //when
//        Long memoryId = activityService.saveMemory(activityCreateDto);
//        Tag tag = tagRepository.findByContent("여행");
//
//        //then
//        System.out.println(memoryId);
//        List<ActivityTagRelation> relations = activityRepository.findByTag("여행");
//        for(ActivityTagRelation relation : relations){
//            System.out.println(relation.getActivity().getContent());
//        }
//    }
//
//    @Test
//    public void 태그저장 () throws Exception {
//        //given
//        String tagString = "#여행 #경주 #유민";
//
//        //when
////        activityService.saveTags(tagString);
//
//        //then
////        tagRepository.findByContent("여행");
//    }
}