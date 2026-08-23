You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-agent/codex-app-agent/AGENTS.md
Use this screen spec: /home/codex-agent/codex-app-agent/screens-shop.md
Project directory: /tmp/sikxx-play-link

Task metadata:
- Asana task gid: 1217484336586855
- Asana task name: GB GW4 C1542
- Asana URL: https://app.asana.com/1/1208304498069546/project/1213586227413017/task/1217484336586855
- App name: Sikxx Play Link
- Company: SIKXX LTD
- Domain: https://sikxx.surf
- Package: sikxx.toys.sikxxplaylink
- Prefix: TNQRS
- Type: shop
- Description: Специфика компании — розничная торговля развивающими играми и игрушками для детей всех возрастов в специализированных магазинах.
Приложение по продаже товаров компании содержит:
Каталог игр и игрушек: полный список товаров (с возможностью сортировки по категориям: например, «Настольные игры», «Конструкторы», «Мягкие игрушки», «Интерактивные роботы»).
История покупок: архив всех приобретенных ранее товаров и подарков.
Корзина: перечень выбранных позиций с формой бронирования заказа.
Логика получения заказа: После подтверждения бронирования пользователь видит баннер с информацией о номере и деталях заказа, а также уведомление о том, что выбранные игры и игрушки зарезервированы и ожидают его в магазине в течение 24 часов.
Настройки приложения содержат:
Название компании.
Версию приложения.
Раздел Customer Support со ссылкой на сайт компании.
Дополнительно: Главную страницу можно разнообразить каруселью с заметками о пользе развивающих игр, обзорами самых популярных игрушек сезона или идеями для совместного семейного досуга.

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Do not create project-local agent instruction files inside /tmp/sikxx-play-link.
3. Implement all required screens/content/data/assets/icon according to the orchestrator AGENTS.md and the screen spec.
4. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
5. Do not push to GitHub, do not update Asana, and do not send Slack.
6. You may run local checks while implementing, but the runner will run quality/build afterward.
7. Keep every Kotlin file conventionally formatted: one statement per line, annotations above declarations, expanded indented Compose blocks, no semicolon-compressed code, and no source line longer than 200 characters.
