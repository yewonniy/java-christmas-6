package io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

class InputValidationTest {

    @Test
    void 방문_날짜_숫자임() {
        String readDate = "5";

        int result = InputValidation.isItInt(readDate);

        Assertions.assertThat(result).isEqualTo(5);
    }

    @Test
    void 방문_날짜_숫자_아님() {
        String readDate = "삼일";

        Assertions.assertThatThrownBy(() -> InputValidation.isItInt(readDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @Test
    void 방문_날짜_범위_맞음() {
        int visitDate = 1;

        int result = InputValidation.dateRangeCheck(visitDate);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void 방문_날짜_범위_틀림() {
        int visitDate = 32;

        Assertions.assertThatThrownBy(() -> InputValidation.dateRangeCheck(visitDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @Test
    void 주문_메뉴_입력_성공() {
        String readMenu = "타파스-1,레드와인-1";

        List<String> result = InputValidation.menuInputValidation(readMenu);

        Assertions.assertThat(result).containsExactly("타파스-1","레드와인-1");
    }

    @ValueSource(strings = {"타파스-1,레드와인2", "타파스", ""})
    @ParameterizedTest
    void 잘못된_주문_메뉴_입력(String readMenus) {
        Assertions.assertThatThrownBy(() -> InputValidation.menuInputValidation(readMenus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @Test
    void 중복_메뉴_입력() {
        List<String> menus = Arrays.asList("시저샐러드-1","시저샐러드-1");

        Assertions.assertThatThrownBy(() -> InputValidation.duplicatedMenu(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @Test
    void 메뉴_중복_없음(){
        List<String> menus = Arrays.asList("시저샐러드-2","레드와인-1");

        List<String> result = InputValidation.duplicatedMenu(menus);

        Assertions.assertThat(result).containsExactly("시저샐러드","레드와인");
    }

    @ValueSource(strings = {"시저샐러드-2,레드와인-0", "시저샐러드-1,레드와인-두개"})
    @ParameterizedTest
    void 메뉴_갯수_오류(String menu) {
        List<String> menus = Arrays.asList(menu.split(","));

        Assertions.assertThatThrownBy(() -> InputValidation.numOfDishes(menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @Test
    void 메뉴_갯수_문제없음() {
        List<String> menus = Arrays.asList("시저샐러드-2","레드와인-1");

        List<Integer> result = InputValidation.numOfDishes(menus);

        Assertions.assertThat(result).containsExactly(2,1);
    }

    @Test
    void 스무개_초과_주문(){
        List<Integer> dishNum = Arrays.asList(1,2,3,18);

        Assertions.assertThatThrownBy(() -> InputValidation.dishNumUnder20(dishNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.");
    }

    @Test
    void 스무개_이하_주문() {
        List<Integer> dishNum = Arrays.asList(1,2,3,4);

        boolean result = InputValidation.dishNumUnder20(dishNum);

        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    void 음료만_주문() {
        List<String> dishName = Arrays.asList("제로콜라","레드와인");

        Assertions.assertThatThrownBy(() -> InputValidation.orderOnlyDrink(dishName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 음료만 주문 시, 주문할 수 없습니다.");
    }

    @Test
    void 다양한_종류_주문() {
        List<String> dishName = Arrays.asList("제로콜라", "초코케이크", "티본스테이크");

        boolean result = InputValidation.orderOnlyDrink(dishName);

        Assertions.assertThat(result).isEqualTo(true);
    }
}