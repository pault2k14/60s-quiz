package paulbthompson.me.a1960squiz;

/**
 * Created by Paul on 6/6/2017.
 */

public class QuestionBank {

    private int currentIndex;
    private MultipleChoiceQuestion[] questionArray;

    public QuestionBank() {

        questionArray = new MultipleChoiceQuestion[mQuestion.length];

        for(int i = 0; i < mQuestion.length; i++) {

            questionArray[i] = new MultipleChoiceQuestion(mQuestion[i], mChoices[i][0],
                    mChoices[i][1], mChoices[i][2], mChoices[i][3],
                    mCorrectAnswer[i]);

        }

        currentIndex = 0;
    }


    private String mQuestion [] = {
            "Who was the father of LSD?",
            "Who assassinated Robert F. Kennedy?",
            "Who was the Johnny Appleseed of LSD?",
            "Who was the author of the book The Doors of Perception?",
            "Which Harvard professor was dismissed due to his involvment in illegal LSD experiments?",
            "Which government agency wanted to use LSD as a mind control drug?",
            "Which Bureau of Narcotics agent secretly dosed citizens of San Francisco with LSD?",
            "What was the Weather Underground?",
            "Which movie was about a young man falling in love with a 79 year old woman?",
            "Which radical group gave free meals, health care, and housing to anyone in the Haight-Ashbury neighborhood of San Francisco?",
            "In what city did the Martin Luther King Jr. make his \"I Have A Dream Speech\"?",
            "On what TV Show did The Beatles appear to kick off their first U.S. tour in 1964?",
            "In what year was the American U-2 spy plane piloted by Francis Gary Powers downed and captured in Russia?",
            "In what year did East Germany erect the Berlin Wall?",
            "Whic President stirred thousands of college students with his creation of the Peace Corps?",
            "Who was the first American to orbit the earth?",
            "In what year was the first James Bond Movie released in the United States?",
            "What was the name of the first James Bond movie?",
            "In 1964 President Johnson was given congressional approval of broad war-making authority in Vietnam with what resolution?",
            "Who did Cassius Clay defeat to win his first heavyweight boxing championship?",
            "In what year was Black Nationlist leader, Malcolm X shot to death?",
            "What year did Star Trek debut on TV?",
            "Who becomes the first African- American Supreme Court Justice in 1967?",
            "What country seized the U.S. Spy ship Pueblo?",
            "In what city was the Democratic National Convention held in 1968 that was marked with violence and protests in the streets?",
            "Who was the first man to walk on the moon?",
            "In which year did the first man walk on the moon?",
            "Who did John F. Kennedy defeat in the 1960 U.S. Presidential election?",
            "Which South African leader was sentenced to life in prison in 1964?",
            "Cult members murdered several people in August of 1969, they were led by who?"

    };

    private String mChoices [][] = {
            {"Timothy Leary", "Albert Hofmann", "Aldous Huxley", "Denis Leary"},
            {"Lee Harvey Oswald", "Joseph Stalin", "Sirhan Sirhan", "No one knows"},
            {"Timothy Leary", "Alfred Hubbard", "John F. Kennedy", "Mary Tyler Moore"},
            {"Jack Lemmon", "John Wayne", "Aldous Huxley", "Richard Alpert"},
            {"E. J. Corey", "Timothy Leary", "Alfred Hubbard", "Steven Pinker"},
            {"National Security Agency", "Federal Bureau of Investigation", "Central Intelligence Agency", "Food and Drug Administration"},
            {"Timothy Leary", "George White", "Alfred Hubbard", "J. Edgar Hoover"},
            {"Radical right-wing terrorist group", "Radical left-wing terrorist group", "Free love commune", "Ping pong team"},
            {"Harold and Maude", "One Flew Over The Cuckoo's Nest", "Bonnie and Clyde", "The Graduate"},
            {"Weather Underground", "Barats and Bereta", "The Diggers", "Loiter Squad"},
            {"Memphis, TN", "Washington D.C.", "Selma, AL", "Montgomery, AL"},
            {"The Ed Sullivan Show", "Shindig", "Hullabaloo", "The Steve Allen Show"},
            {"1960", "1961", "1962", "1964"},
            {"1960", "1961", "1962", "1966"},
            {"John F. Kennedy", "Dwight D. Eisenhower", "Richard M. Nixon", "Lyndon B. Johnson"},
            {"John Glenn", "Neil Armstrong", "Gary Powers", "Gordon Cooper"},
            {"1962", "1963", "1964", "1965"},
            {"Dr. No", "Goldfinger", "Thunderball", "From Russia with Love"},
            {"Vietnam Broad War Powers Resolution", "Tonkin Gulf Resolution", "Warren Commission Resolution", "Economic Opportunity for Vietnam Resolution"},
            {"Floyd Patterson", "Sonny Liston", "George Foreman", "Joe Louis"},
            {"1964", "1965", "1966", "1967"},
            {"1965", "1966", "1967", "1968"},
            {"Thurgood Marshall", "Edward White", "oger Chaffee", " John Wooden"},
            {"North Vietnam", "South Korea", "Russia", "North Korea"},
            {"Chicago", "Los Angeles", "New York", "Boston"},
            {"John Glenn", "Neil Armstrong", "Gary Powers", "Gordon Cooper"},
            {"1966", "1967", "1968", "1969"},
            {"Dwight D. Eisenhower", "Richard M. Nixon", "John Glenn", "Lyndon B. Johnson"},
            {"Charles de Gaulle", "Harold Macmillan", "Nelson Mandela", "Charles Robberts Swart"},
            {"Richard M. Nixon", "Charles Manson", "Pope Paul VI", "Martin Luther King Jr."}
    };

    private String mCorrectAnswer [] = {
            "Albert Hofmann",
            "Sirhan Sirhan",
            "Alfred Hubbard",
            "Aldous Huxley",
            "Timothy Leary",
            "Central Intelligence Agency",
            "George White",
            "Radical left-wing terrorist group",
            "Harold and Maude",
            "The Diggers",
            "Washington D.C.",
            "The Ed Sullivan Show",
            "1960",
            "1961",
            "John F. Kennedy",
            "John Glenn",
            "1963",
            "Dr. No",
            "Tonkin Gulf Resolution",
            "Sonny Liston",
            "1965",
            "1966",
            "Thurgood Marshall",
            "North Korea",
            "Chicago",
            "Neil Armstrong",
            "1969",
            "Richard M. Nixon",
            "Nelson Mandela",
            "Charles Manson"
    };

    public int getIndex() {
        return currentIndex;
    }

    public void setIndex(int x) {
        currentIndex = x;
    }

    public int getSize() { return questionArray.length; };

    public String getQuestionText() {
        return questionArray[currentIndex].questionText;
    }

    public String getChoice1Text() {
        return questionArray[currentIndex].choice1Text;
    }

    public String getChoice2Text() {
        return questionArray[currentIndex].choice2Text;
    }

    public String getChoice3Text() {
        return questionArray[currentIndex].choice3Text;
    }

    public String getChoice4Text() {
        return questionArray[currentIndex].choice4Text;
    }

    public String getCorrectChoiceText() {
        return questionArray[currentIndex].correctChoiceText;
    }

    public void nextQuestion() {
        if(isNextQuestion()) {
            ++currentIndex;
        }
    }

    public boolean isNextQuestion() {
        return currentIndex < questionArray.length - 1;
    }
}
