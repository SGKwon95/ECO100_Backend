package kr.mapo.eco100.error;

public class ChallengeNotFoundException extends RuntimeException{
    public ChallengeNotFoundException(String message) {
        super(message);
    }
}
