package kr.mapo.eco100.error;

public class ChallengePostNotFoundException extends RuntimeException{
    public ChallengePostNotFoundException(String message) {
        super(message);
    }
}
