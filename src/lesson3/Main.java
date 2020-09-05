package lesson3;


import lesson3.phoneBook.PhoneBook;

import java.util.*;

/**
 * Homework for lesson #3
 * <p>
 * 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем)
 * . Посчитать сколько раз встречается каждое слово.
 * <p>
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий
 * и телефонных номеров. В этот телефонный справочник с помощью метода add() можно
 * добавлять записи. С помощью метода get() искать номер телефона по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько телефонов
 * (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все
 * телефоны.
 * Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в
 * телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес),
 * делать взаимодействие с пользователем через консоль и т.д.). Консоль желательно
 * не использовать (в том числе Scanner), тестировать просто из метода main()
 * прописывая add() и get().
 *
 * @author Valeriy Lazarev
 * @since 02.09.2020
 */

public class Main {
    public static void main(String[] args) {

        /*Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем)*/
        TreeSet<String> treeSet = new TreeSet<>(Arrays.asList(StringResources.stringResources));
        System.out.println(treeSet.toString());


        /*Посчитать сколько раз встречается каждое слово.*/
        Map<String, Integer> map = wordCount(treeSet);

        for (String s : map.keySet()) {
            System.out.println("Слово: \"" + s + "\"" + " встречается " + map.get(s) + " раз.");
        }

        /* * 2. Написать простой класс Телефонный Справочник, который хранит в себе список фамилий
         * и телефонных номеров.
         * Следует учесть, что под одной фамилией может быть несколько телефонов
         * (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все
         * телефоны.*/
        PhoneBook phoneBook = new PhoneBook();

        /* В этот телефонный справочник с помощью метода add() можно
         * добавлять записи. */
        phoneBook.add("ivanov", "555-5555");
        phoneBook.add("petrov", "333-3333");
        phoneBook.add("sidorov", "444-444");
        phoneBook.add("pavlov", "111-1111");
        phoneBook.add("pavlov", "444-4444");

        /*С помощью метода get() искать номер телефона по фамилии.*/
        System.out.println("Pavlov: " + phoneBook.get("pavlov"));

        System.out.println(phoneBook.toString());

        System.out.println("sidorov: " + phoneBook.get("sidorov"));
    }

    private static Map<String, Integer> wordCount(Collection<String> treeSet) {
        Map<String, Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>(treeSet);

        for (int i = 0; i < StringResources.stringResources.length; i++) {
            String word = StringResources.stringResources[i];
            if (list.contains(word)) {
                if (map.get(word) != null) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        return map;
    }
}
