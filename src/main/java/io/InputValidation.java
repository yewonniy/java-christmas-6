package io;

import enums.Menu;

import java.util.*;

public class InputValidation {
    static final int eventStartDate = 1;
    static final int eventFinishDate = 31;
    static final int maxOrderNum = 20;
    static final int minOrderNum = 1;
    public static int isItInt(String readVisitDate) {
        int visitDate;
        try {
            visitDate = Integer.parseInt(readVisitDate);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
        return visitDate;
    }

    public static int dateRangeCheck(int visitDate) {
        if (eventStartDate > visitDate || eventFinishDate < visitDate) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
        return visitDate;
    }

    public static List<String> menuInputValidation(String readMenu) {
        List<String> menus = Arrays.asList(readMenu.split(","));
        for (String menu : menus) {
            try {
                int dishPrice = Integer.parseInt(menu.split("-")[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
        }
        return menus;
    }

    public static List<String> duplicatedMenu(List<String> menus) {
        List<String> dishNames = new ArrayList<String>();
        for (String menu: menus) {
            dishNames.add(menu.split("-")[0]);
        }
        Set<String> dishNameWithoutDuplicate = new HashSet<>(dishNames);
        if (dishNames.size() != dishNameWithoutDuplicate.size())
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");

        return dishNames;
    }

    public static List<Integer> numOfDishes(List<String> menus) {
        List<Integer> dishNum = new ArrayList<Integer>();
        int dishNumber;
        for (String menu: menus) {
            try {
                dishNumber = Integer.parseInt(menu.split("-")[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }
            dishNum.add(dishNumber);
            if (dishNumber < minOrderNum)
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
        return dishNum;
    }


    public static boolean dishNumUnder20(List<Integer> dishNum) {
        if (dishNum.stream().mapToInt(Integer::intValue).sum() > maxOrderNum)
            throw new IllegalArgumentException("[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.");
        return true;
    }

    public static boolean orderOnlyDrink(List<String> dishNames) {
        Menu menu;
        Set<String> menuType = new HashSet<>();

        for (String dishName : dishNames) {
            menu = Menu.menuMatch.get(dishName);
            menuType.add(menu.getType());
        }

        if (menuType.size() == 1 && menuType.contains("Drink"))
            throw new IllegalArgumentException("[ERROR] 음료만 주문 시, 주문할 수 없습니다.");
        return true;
    }
}
