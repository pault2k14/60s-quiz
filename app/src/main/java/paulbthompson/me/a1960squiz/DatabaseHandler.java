package paulbthompson.me.a1960squiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 6/10/2017.
 */

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questionManager";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String KEY_QUESTION_ID = "questionId";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER_ONE = "answerOne";
    private static final String KEY_ANSWER_TWO = "answerTwo";
    private static final String KEY_ANSWER_THREE= "answerThree";
    private static final String KEY_ANSWER_FOUR = "answerFour";
    private static final String KEY_CORRECT_ANSWER = "correctAnswer";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_QUESTION_ID + " INTEGER PRIMARY KEY, "
                + KEY_QUESTION + " TEXT, " + KEY_ANSWER_ONE + " TEXT, "
                + " TEXT, " + KEY_ANSWER_TWO + " TEXT, " + KEY_ANSWER_THREE
                + " TEXT, " + KEY_ANSWER_FOUR + " TEXT, " + KEY_CORRECT_ANSWER
                + " TEXT" + ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addQuestion(MultipleChoiceQuestion mcq) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, mcq.getQuestion());
        values.put(KEY_ANSWER_ONE, mcq.getAnswerOne());
        values.put(KEY_ANSWER_TWO, mcq.getAnswerTwo());
        values.put(KEY_ANSWER_THREE, mcq.getAnswerThree());
        values.put(KEY_ANSWER_FOUR, mcq.getAnswerFour());
        values.put(KEY_CORRECT_ANSWER, mcq.getCorrectAnswer());

        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public MultipleChoiceQuestion getQuestion(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS,
                new String[] {
                KEY_QUESTION_ID, KEY_QUESTION, KEY_ANSWER_ONE, KEY_ANSWER_TWO,
                KEY_ANSWER_THREE, KEY_ANSWER_FOUR, KEY_CORRECT_ANSWER },
                KEY_QUESTION_ID + "=?",
                new String[] { String.valueOf(questionId) },
                null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        return new MultipleChoiceQuestion( cursor.getString(0),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6)
        );

    }

    public List<MultipleChoiceQuestion> getAllQuestions() {
        List<MultipleChoiceQuestion> questionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(
                        cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5),
                        cursor.getString(6)
                );
                questionList.add(mcq);
            } while(cursor.moveToNext());
        }

        return questionList;
    }

    public Cursor getAllQuestionsCursor() {
        List<MultipleChoiceQuestion> questionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        return cursor;
    }

    public int getQuestionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int updateQuestion(MultipleChoiceQuestion mcq) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, mcq.getQuestion());
        values.put(KEY_ANSWER_ONE, mcq.getAnswerOne());
        values.put(KEY_ANSWER_TWO, mcq.getAnswerTwo());
        values.put(KEY_ANSWER_THREE, mcq.getAnswerThree());
        values.put(KEY_ANSWER_FOUR, mcq.getAnswerFour());
        values.put(KEY_CORRECT_ANSWER, mcq.getCorrectAnswer());

        return db.update(TABLE_QUESTIONS, values, KEY_QUESTION_ID + " = ?",
                new String[] { mcq.getQuestionId() });
    }

    public void deleteQuestion(MultipleChoiceQuestion mcq) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTIONS, KEY_QUESTION_ID + " = ?",
                new String[] {mcq.getQuestionId()});
        db.close();
    }
}
