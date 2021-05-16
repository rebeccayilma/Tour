import React, { useState } from 'react';

export function NewRatingForm(props) {
    const [score, setScore] = useState('');
    const submit = props.submit;
    const activity = props.activity;

    const handleSubmit = (evt) => {
        evt.preventDefault();
        submit(score, activity);
    }

    const handleScoreChange = (evt) => {
        setScore(evt.target.value);
    }

    return (
        <div className="NewRating">
        <form onSubmit={handleSubmit}>
            
            <label>Activity</label>
            <input type="text" data-test="activity_id" value={activity.activity_id} readOnly />
            <input type="text" data-test="activity" value={activity.info} readOnly />

            <label>Score</label>
            <input type="range" min="1" max="5" data-test="score" value={score} onChange={handleScoreChange}/>

            <input type="submit" value="Submit" data-test="submit" />
        </form>
        </div>
    );
}