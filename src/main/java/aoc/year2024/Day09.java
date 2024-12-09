package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.ArrayList;
import java.util.List;

public class Day09 implements Day {
    record File(Integer size, Integer value) {

    }

    @Override
    public String solvePart1(String filePath) {
        List<Integer> ints = Util.getResourceAsIntegerList(filePath);
        List<Integer> charList = new ArrayList<>();
        int fileId = 0;

        for (int i = 0; i < ints.size(); i++) {
            int count = ints.get(i);
            if (i % 2 == 0) {
                for (int j = 0; j < count; j++) {
                    charList.add(fileId);
                }
                fileId++;
            } else {
                for (int j = 0; j < count; j++) {
                    charList.add(-1);
                }
            }
        }

        for (int i = charList.size() - 1; i >= 0; i--) {
            if (charList.get(i) != -1) {
                for (int j = 0; j < i; j++) {
                    if (charList.get(j) == -1) {
                        charList.set(j, charList.get(i));
                        charList.set(i, -1);
                        break;
                    }

                }
            }
        }
        long sum = 0;

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i) != -1) {
                sum += (long) i * charList.get(i);
            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2(String filePath) {
        List<Integer> ints = Util.getResourceAsIntegerList(filePath);
        List<Integer> charList = new ArrayList<>();
        int fileId = 0;

        // Parse input into a block representation of the disk
        for (int i = 0; i < ints.size(); i++) {
            int count = ints.get(i);
            if (i % 2 == 0) {
                for (int j = 0; j < count; j++) {
                    charList.add(fileId);
                }
                fileId++;
            } else {
                for (int j = 0; j < count; j++) {
                    charList.add(-1);
                }
            }
        }

        System.out.println("Initial disk state: " + charList);

        for (int id = charList.size() - 1; id >= 0; id--) {
            int fileVal = charList.get(id);

            if (fileVal == -1) {
                continue;
            }

            int fileSize = 0;
            for (Integer integer : charList) {
                if (integer == fileVal) {
                    fileSize++;
                }
            }

            int targetPosition = -1;
            for (int i = 0; i <= charList.size() - fileSize; i++) {
                boolean canMove = true;
                for (int j = 0; j < fileSize; j++) {
                    if (charList.get(i + j) != -1) {
                        canMove = false;
                        break;
                    }
                }
                if (canMove) {
                    targetPosition = i;
                    break;
                }
            }

            if (targetPosition != -1) {
                for (int i = 0; i < charList.size(); i++) {
                    if (charList.get(i) == id) {
                        charList.set(i, -1);
                    }
                }
                for (int i = 0; i < fileSize; i++) {
                    charList.set(targetPosition + i, id);
                }
            }
        }

        // Calculate the checksum
        long sum = 0;
        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i) != -1) {
                sum += (long) i * charList.get(i);
            }
        }

        System.out.println("Final disk state: " + charList);

        return String.valueOf(sum);
    }

}
