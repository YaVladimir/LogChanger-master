# LogChanger-master
Проект для команды Депозиты ЮЛ, СБТ.
Так как целевая архитектура предполагает работу модуля на нескольких нодах, 
а запросы на ноды приходят в случайном порядке, то логи пишутся в несколько файлов одновременно: часть туда, часть сюда.

Программа склеивает лог файлы начинающиеся с "dul" и оканчивающиеся на ".log",
согласно timestamp внутри, в хронологическом порядке.

Результат записывает в текстовый файл, название которого содержит время последней записи в лог-файлах.

Запускать в директории содержащей необходимые файлы для склеивания.
