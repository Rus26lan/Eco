package com.rundgrun.eco.domain;

import com.rundgrun.eco.domain.models.Pak;
import com.rundgrun.eco.domain.models.PakEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PakResponseAnalyser {
    public static ArrayList<PakEvent> analyzeEventLog(String response) {
        ArrayList<PakEvent> list = new ArrayList<>();
        String regToSection = "[\\[\\]]";
        String[] result = response.split(regToSection);
        String regToPcs = ",";
        String[] mls = result[5].split(regToPcs);
        String[] names = result[7].split(regToPcs);
        String[] volts = result[9].split(regToPcs);
        String[] modems = result[11].split(regToPcs);
        String[] airnods = result[13].split(regToPcs);
        for (int i = 0; i < mls.length; i++) {
            PakEvent pakEvent = new PakEvent(mls[i], names[i].replace("\"", ""), volts[i], modems[i], airnods[i]);
            list.add(pakEvent);
        }
        Collections.reverse(list);
        return list;
    }


    public static ArrayList<Pak> analyzeAllPak(String response) {
        ArrayList<Pak> list = new ArrayList<>();
        String regToSection = "[\\[\\]]";
        String[] result = response.split(regToSection);
        String regToPcs = ",";
        String[] names = result[7].split(regToPcs);
        String[] volts = result[9].split(regToPcs);
        String[] modems = result[11].split(regToPcs);
        String[] airnods = result[13].split(regToPcs);
        String[] description = result[21].split("\",\"");
        for (int i = 0; i < names.length; i++) {
            Pak pak = new Pak(names[i].replace("\"", ""), volts[i], modems[i], airnods[i], description[i].replace("\"", ""));
            list.add(pak);
        }
        return list;
    }

    public static void listToFile(ArrayList<PakEvent> list) throws IOException {
        FileWriter writer = new FileWriter("OutPutRAW.txt");
        list.forEach((pakEvent) -> {
            try {
                writer.write(pakEvent.toString());
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    public static List<PakEvent> filterToRangeDate(ArrayList<PakEvent> list, long dateFrom, long dateTo) {
        return list.stream().filter(p -> (p.mls >= dateFrom && p.mls <= (dateTo + 86400000))).collect(Collectors.toList());
    }

    public static List<PakEvent> filterToNum(ArrayList<PakEvent> list, int name) {
        return list.stream().filter(p -> (p.name == name)).collect(Collectors.toList());
    }

    public static Set<Integer> getNumsSet(ArrayList<PakEvent> list) {
        Set<Integer> set = list.stream().map(p -> (p.name)).collect(Collectors.toSet());
        return set;
    }

}
