# TravelBuddy
Android Application
https://youtu.be/LTXpDyCsTd4

## Description : 
This is a convenient travel partner application to accompany users on their travel keeping all their documents in one place.
## Motivation : 
My motivation is to develop a modern instinctive application for travel. During one summer I was visiting multiple countries and I had tickets from different applications, it was confusing finding the correct application for the situation. This inconvenience inspired me to develop such application.
## MoSCoW : 
- Must have : 
    - Ability to capture photos
    - Send the photos to the cloud
    - Google login services
- Should have : 
    - Travel reminder notification
    - Travel checklist
    - Offline mode
- Could have :
    - Download the photos from the cloud
    - Checklist reminder if checklist isn't completed
    - Travel info of the country
    - Sharing trip system
    - Dark mode
    - Map of chosen location
- Won't have :
    - Friend system 


## Things that work : 
- Authentication using Google Login through Firebase
- Realtime database and Cloud storage for data persistance used mainly (90%) for lists
- Swipe to delete is used on everything in the app (anything that could be deleted is deleted using the swipe to the left)
- Dialog fragments maybe deprecated (because android loves changes) so they're used to create objects as it's a more user friendly way
- The camera took 2 weeks to implement and it has been working for a long time (the developper just didn't know where to look in the phone files)
- Speaking of files, the file picker uses a predefined file path to access photos, the photos are also saved onto the cloud so even if the application is removed local files will be destroyed but their souls live on in the clouds
- The checklist works a bit too well, it is easily spammable and also saved the boolean
- Implementing documents to trips use documents that are uploaded to the database
- Only authenticated users can read or write in the database
- Modifications to existing trips are possible
- Preview of photos for the documents or for the trips are done using the Picasso Android library (works with url ending in image format only)
- The user is no longer trapped in the application and can log out

## Minor Hugs :
- User is disconnected on opening file picker
- Logging out causes a database error (on cancelled database is triggered weirdly)
- Cannot remove the stupid child event listener so please bare with the developper
- Updating doesn't update in real time due to child event listener
- After updating a trip, this can cause a path error to the database or a corrupted entry into the database
- There is no logout service
- Notifications were not implemented
- The photos are saved onto the cloud but cannot be accessed from the application
- The date is just a string
- Dark mode never saw light of the application
- Who needs friends? (I do) Sadly a friend system didn't get implemented
- Also no one can flex on this app by sharing a trip but they can still go see someone physically and show the app directly (not Virus friendly though)
- Travel info of the country isn't implemented due to the developper not searching for a suitable API
- That also means countries have to be typed up
- There is no map (the designer of this application was too ambitious)
- After coding for almost a hundred hours in total, at the end of the day, the developper is seeing things more blurry even though the developper has glasses

# I would like to thank certain people with my code :
- My android teachers from VIA University Horsens
    - Jakob Knop Rasmussen
    - Kasper Knop Rasmussen
- The Stack Overflow Community
- Android youtubers :
    - Ali Farhat
    - Ambar Hasbiyatmoko
    - an Erik
    - AndroChunk
    - Android Coding
    - Atif Pervalz
    - Azamsharp
    - Belal Khan
    - Bhuwan Mahato
    - Careless Coders
    - CMDev
    - CodeWithMazn
    - Coding 22Rials
    - Coding in Flow
    - Coding with TEA
    - CodingSTUFF
    - CodingWithMitch
    - Cubix Sol
    - Daniyal Zakir
    - Dipesh Rai
    - Fery Shah
    - Filip Vujovic
    - Firebase
    - Foxandroid
    - Graven Développment
    - Hemant Kumar
    - Ketul Patel
    - Let's Study
    - Matt Boutell
    - Md Jamal
    - Papaya Coders
    - Penguin Coders
    - Prego Coding Classes
    - Programming Experts 
    - Rajjan Sharma 
    - Sagar S
    - Sanvi Infotech
    - Simplified Coding
    - Stevdza - San
    - Technical Skillz
    - The Net Ninja
    - TVAC Studio
    - U4Universe
    - uNicoDev
    - YoursTRULY
    - Alan Ranjoni
