package com.sleekydz86.kanana.global.gateway;

import com.sleekydz86.kanana.application.port.CompletionResult;
import com.sleekydz86.kanana.application.port.LlmInferencePort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MockLlmInferenceAdapter implements LlmInferencePort {

    @Override
    public CompletionResult complete(String modelId, String message) {
        String model = (modelId != null && !modelId.isBlank())
                ? modelId : "kanana-2-30b-a3b-instruct-2601";

        String input = message.trim();
        int len = input.length();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder sb = new StringBuilder();

        if (containsAny(input, "안녕", "하이", "hello", "hi")) {
            sb.append("안녕하세요! 무엇을 도와드릴까요?\n\n");
        } else if (containsAny(input, "고마워", "감사", "thank")) {
            sb.append("천만에요! 도움이 되었다면 기쁩니다.\n\n");
        } else if (containsAny(input, "뭐야", "누구", "소개", "자기소개")) {
            sb.append("저는 Kanana-2 모델입니다. 카카오에서 개발한 한국어 특화 대규모 언어 모델이에요. "
                    + "30B 파라미터 중 3B를 활성화하는 MoE(Mixture of Experts) 구조를 사용합니다.\n\n");
        } else if (input.endsWith("?") || input.endsWith("??") || input.endsWith("까?")
                || input.endsWith("까") || input.endsWith("나?") || input.endsWith("나")
                || input.endsWith("요?") || containsAny(input, "어떻게", "왜", "뭐", "어디", "언제", "얼마")) {
            sb.append("\"").append(truncate(input, 60)).append("\"에 대해 답변드리겠습니다.\n\n");
            sb.append("현재 Mock 모드이므로 실제 AI 추론 결과는 아니지만, 질문을 정상적으로 수신했습니다. ");
            sb.append("실제 환경에서는 Kanana-2 모델이 이 질문에 대해 상세한 답변을 생성합니다.\n\n");
        } else {
            sb.append("\"").append(truncate(input, 60)).append("\" — 메시지를 잘 받았습니다.\n\n");
        }

        sb.append("---\n");
        sb.append("Mock 모드 (실제 LLM 미연결)\n");
        sb.append("- 모델: ").append(model).append("\n");
        sb.append("- 입력 길이: ").append(len).append("자\n");
        sb.append("- 처리 시각: ").append(now).append("\n");
        sb.append("- 실제 응답을 받으려면 vLLM 서버 실행 후 mock-enabled=false로 설정하세요.");

        return new CompletionResult(sb.toString(), model);
    }

    private static boolean containsAny(String text, String... keywords) {
        String lower = text.toLowerCase();
        for (String kw : keywords) {
            if (lower.contains(kw.toLowerCase())) return true;
        }
        return false;
    }

    private static String truncate(String text, int maxLen) {
        return text.length() > maxLen ? text.substring(0, maxLen) + "..." : text;
    }
}
