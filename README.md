# CyntekaTest
Консольное приложение на Java, которое читает из файла input.txt входные данные в следующей последовательности:

программа читает число n;
программа читает n строк;
программа читает число m;
программа читает m строк;

После этого программа должна сопоставить максимально похожие строки из первого множества (n) со строками из второго множества(m) 
одна к одной и вывести результат в файл output.txt
Для вычисления коэффициента схожести строк используется Сходство Джаро — Винклера (https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance)


После запуска программы введите путь к входному файлу, например 
src/main/resources/input.txt

В папке src/main/resources есть подготовленные файлы с примерами из тестового задания.
src/main/resources/input.txt
src/main/resources/input2.txt
src/main/resources/input3.txt

Также, для наглядности, программа печатает результат на экране.