package no.ntnu.helipeli;

import java.util.HashMap;
import java.util.Map;

import no.ntnu.helipeli.observer.Observer;

/**
 *
 */
public class Achievements extends Observer {
    private HashMap<Integer, String> achievementList;

    public Achievements(){
        achievementList = new HashMap<Integer, String>();
    }

    @Override
    public void OnNotify(String achievementText, int achievementKey) {
        if(achievementList.containsKey(achievementKey)){
            return;
        }
        achievementList.put(achievementKey, achievementText);
        System.out.println("New Achievement Completed: Achievement ID " + achievementKey + " - " + achievementText);
    }

    /**
     * Print all achievements the player currently has.
     */
    public void printAllAchievements(){
        for(Map.Entry<Integer, String> achievement : achievementList.entrySet()){
            System.out.println("New Achievement Completed: Achievement ID " + achievement.getKey() + " - " + achievement.getValue());
        }
    }
}
