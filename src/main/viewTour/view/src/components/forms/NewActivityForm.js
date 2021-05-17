import React, { useState } from 'react';

export function NewActivityForm(props) {
    const [info, setInfo] = useState('');
    const submit = props.submit;
    const place = props.place;

    const handleSubmit = (evt) => {
        evt.preventDefault();

        if (!info) {
            // TODO: inform error
            // return this.setState({ error: 'Info is required' });
            return;
        }

        submit(info, place);
    }

    const handleInfoChange = (evt) => {
        setInfo(evt.target.value);
    }

    return (
        <div className="NewActivity">
        <form className="w-full max-w-sm" onSubmit={handleSubmit}>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">
            
            <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-full-name">Place {place.name}</label>
            <br/>
            <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-full-name">Info</label>
                </div>
                <div className="md:w-2/3">
            <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" data-test="info" value={info} onChange={handleInfoChange} />
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