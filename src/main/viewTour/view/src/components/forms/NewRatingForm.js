import React, { useState } from 'react';
import'../../assets/main.css';
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
        <form className="w-full max-w-sm" onSubmit={handleSubmit}>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">
            <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-full-name">Activity</label>
                </div>
                <div className="md:w-2/3">
            <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name"type="text" data-test="activity_id" value={activity.activity_id} readOnly />
            <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" type="text" data-test="activity" value={activity.info} readOnly />
                </div>
            </div>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">
            <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-latitude">Score</label>
                </div>
                <div className="md:w-2/3">
            <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name"  type="range" min="1" max="5" data-test="score" value={score} onChange={handleScoreChange}/>
                </div>
            </div>
            <div className="md:flex md:items-center">
                <div className="md:w-1/3"></div>
                <div className="md:w-2/3">
            <input className="shadow bg-purple-500 hover:bg-purple-400 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded" type="submit" value="Submit" data-test="submit" />
                </div>
            </div>
                </form>
        </div>
    );
}