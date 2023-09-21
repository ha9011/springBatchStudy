package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Configuration // 하나의 배치 JOB을 정의하고 빈 설정
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; // Job을 생성하는 빌더 팩토리
    private final StepBuilderFactory stepBuilderFactory; // Step을 생성하는 빌더 팩토리

    @Bean
    public Job helloJob() {
        return this.jobBuilderFactory.get("helloJob") // Job 생성
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1") // Step 생성
                .tasklet(new Tasklet() {  // tasklet - step 안에서 '단일' 태스크로 수행되는 로직 구현
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(" ============================");
                        System.out.println(" >> Hello Spring Batch");
                        System.out.println(" ============================");
                        return RepeatStatus.FINISHED; //  null, finished = 반복없이 끝
                    }
                })
                .build();
    }
    public Step helloStep2() {
        AtomicInteger count = new AtomicInteger();
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" ============================");
                    System.out.println(" >> Step2 has executed");
                    System.out.println(" >> count : " + count);
                    System.out.println(" ============================");
                    if(count.get() == 5) {
                        return RepeatStatus.FINISHED;
                    }
                    count.addAndGet(1);
                    return RepeatStatus.CONTINUABLE;
                })
                .build();
    }
}
