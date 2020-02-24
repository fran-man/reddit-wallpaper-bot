# reddit-wallpaper-bot
## Overview
An app to run daily jobs searching reddit for wallpapers that match a certain query.

Currently it searches for 4K (3840x2160) wallpapers from [/r/wallpaper](https://www.reddit.com/r/wallpaper/)

They are then posted (6pm London Time) to [my subreddit](https://www.reddit.com/r/WallpaperBot/)

## TODO
1. Add more versatility so that searches can be run on multiple subreddits
2. Comment on all posts that are used to inform the original poster that they have been featured - this will also get the word out there about this app
3. Change the quartz setup from using Spring Bean-ey approach to improve scalability and maintainability - for now it is fine but if I wanted to add 10+ jobs it would get ridiculous, would be much easier to loop through a list and build them all in one go.
4. Do a post for each result rather than an aggregation?