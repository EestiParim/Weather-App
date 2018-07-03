# Weather-App
Simple and lightweight weather app that takes your phones location and shows nearby weather station data in beutiful manner. It was made as test assignment for Mooncascade.

# Prerequisites
App was developed in Android Studio 3.13 and written in Java.

# Tasks
All mandatory tasks are completed, small exception could be maintainable code, since in last minute I thought it would be interesting to test out the GPS functionality.

# Additional Tasks
- [ ] Localisation support - At the beginning I was excited about that one, but when I found out that the openweathermaps dont support Estonian, I started looking at other tasks. Also I have done Localisation before, so it would not have been that interesting
- [X] Weather forecast for the city the user is located in - It works fine but code needs a lot refactoring- it doesnt really show only the forecast for the city user is located but rather gives you 10 closest weather stations to choose from.
- [X] Weather forecast for the city the user is located in - I did not plan on doing that at the beginning, but when I finished mandatory tasks I surfed duckduckgo and found out that Vollay has excellent support for caching, so I implemented that one too. Not only does he cache for cases whem you dont have internet- It also uses cache in other scenarios aswell, which is really good when the API has limited request, like the one I used has.
- [X] Display temperature info as words - That was the first Task I finished, since it required specific design. I am really not fan of the looks tho, I prefer old good numeral digits. But implementation itself was at the same time easy and hard. At first I spent about a half an hour trying to build the parser myself, before I found already made library. The Library also has localisation support so I hoped I would use that one too, but unfortunately not this time.
- [ ] Remember user latest chosen city and display this city information - The one task I knew I would skip- Proeksperts test assignment had also similar task and I havent found a good way to do it, all solutions I have found in duckduckgo seem really hackish and bad.
- [X] Show icons for different weathers - I knew from the beginning that I would do that one, because its really easy and also icons always help design and UX. I found really great icon pack from material design community, Implementing the icons was also really fast and smooth.
- [X] Nice UI - Truth be told, I am not very pleased with the current UI I made- At first I thought it would be cool to contrast two views- make one light and other one dark, but in the final product I dont think that was correct aproach for that assignment. I also wanted to add different background images, but then thought for demo app, it doesnt really matter since the images I have currently installed are pretty good for few minutes someone uses the app. Overall I felt assignment itself made limitations to design- I had to write weather info as words and also display how strong the wind was. Those two things made forecast display too cluttered. Also on forecast display I choose to show weather in every 3 hours, at first I showed only 1 forecast per day, but in that way, having free API, you could only show 5 days worth of forecast, and it felt a bit empty, so I decided to show 8 per day.

# screenshots
![screenshot1](https://i.imgur.com/y6UBQex.jpg)
![screenshot2](https://i.imgur.com/jC2dhN8.jpg)
![screenshot3](https://i.imgur.com/GAcr9g3.jpg)
![screenshot4](https://i.imgur.com/ebG8EEK.jpg)







