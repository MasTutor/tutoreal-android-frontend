package com.mastutor.tutoreal.data.dummy

data class TutorDummy (
    val name: String,
    val about: String,
    val skills: String,
    val speciality: String,
    val job: String,
    val photoUrl: String,
    val price: Long,
    val percentage: Double
)

object TutorData {
    val tutors = listOf(
        // make 10 of this.
        TutorDummy (
            "Charlie",
            "Ooo yeah baby, this is what I am waiting for",
            "This is what it's all about ooo",
            "Gaslighting",
            "Gaslighter",
            "https://upload.wikimedia.org/wikipedia/commons/b/b6/Cr1TiKaL_in_2022.jpg",
            690000,
            98.0
        ),
        TutorDummy (
            "Gnorts Statham",
            "Lorem ipsum dolor sit amet, consectetura dipiscing elit. Cras sem nulla, mollis sed lorem ut, dictum dignissim justo. Donec consequat risus vel ligula condimentum maximus. Integer non tortor turpis. Suspendisse nisl augue, pulvinar ut rutrum fermentum, bibendum ultricies tellus. Aenean tristique ex vel tellus fringilla, in pretium dolor sollicitudin. vel rutrum.",
            "Java, Kotlin, Android",
            "Teknologi",
            "Software Engineer",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            75000000,
            98.0
        ),
        TutorDummy (
            "Bob",
            "Enthusiastic about data science and machine learning",
            "Python, Pandas, Scikit-learn",
            "Data Science",
            "Data Scientist",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            80000000,
            98.0
        ),
        TutorDummy (
            "Eve",
            "Experienced in graphic design and UI/UX",
            "Adobe Creative Suite, Figma",
            "Graphic Design",
            "UI/UX Designer",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            70000000,
            98.0
        ),
        TutorDummy (
            "David",
            "Passionate about mathematics and teaching",
            "Algebra, Calculus, Statistics",
            "Mathematics",
            "Mathematics Teacher",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            72000000,
            98.0
        ),
        TutorDummy (
            "Grace",
            "Fitness freak and nutrition expert",
            "Personal Training, Nutrition",
            "Fitness",
            "Fitness Trainer",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            90000000,
            98.0
        ),
        TutorDummy (
            "Frank",
            "Expert in cybersecurity and ethical hacking",
            "Cybersecurity, Penetration Testing",
            "Cybersecurity",
            "Ethical Hacker",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            85000000,
            98.0
        ),
        TutorDummy (
            "Helen",
            "Passionate about environmental science",
            "Climate Change, Ecology",
            "Environmental Science",
            "Environmental Scientist",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            78000000,
            98.0
        ),
        TutorDummy (
            "Jack",
            "Avid reader and literature enthusiast",
            "English Literature, Creative Writing",
            "Literature",
            "English Literature Teacher",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            73000000,
            98.0
        ),
        TutorDummy (
            "Mia",
            "Art lover and experienced painter",
            "Oil Painting, Watercolor",
            "Fine Arts",
            "Artist",
            "https://i.ytimg.com/vi/rZ4bDCZvlg0/maxresdefault.jpg",
            82000000,
            98.0
        )
    )
}