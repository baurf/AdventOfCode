package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.ArrayList;
import java.util.List;

public class Day09 implements Day {
    @Override
    public String solvePart1(String filePath) {
        List<Integer> charList = getFragmentedFile(filePath);

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
        return getCheckSum(charList);
    }

    @Override
    public String solvePart2(String filePath) {
        List<Integer> charList = getFragmentedFile(filePath);

        // Falls das irgendwenn irgendeppert ahluegt ja ich weiss es isch gruusig
        // Aber ich han susht no es lebe und ehrlichgseit fett kei bock meh uf de code grad
        for (int id = charList.size() - 1; id >= 0; id--) {
            int fileVal = charList.get(id);

            if (fileVal == -1) {
                continue;
            }

            int fileSize = 1;
            for (int f = id - 1; f >= 0; f--) {
                if (charList.get(f) == fileVal) {
                    fileSize++;
                } else {
                    break;
                }
            }

            int targetPosition = -1;
            for (int i = 0; i <= id - fileSize; i++) {
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
                for (int i = 0; i < fileSize; i++) {
                    charList.set(id - i, -1);
                    charList.set(targetPosition + i, fileVal);
                }
            }
            id = id - (fileSize - 1);
        }

        return getCheckSum(charList);
    }

    private static List<Integer> getFragmentedFile(String filePath) {
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
        return charList;
    }

    private String getCheckSum(List<Integer> charList) {
        long sum = 0;
        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i) != -1) {
                sum += (long) i * charList.get(i);
            }
        }

        return String.valueOf(sum);
    }

}
