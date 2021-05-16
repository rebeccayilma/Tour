import React, { useState } from 'react';

export function NewActivityForm(props) {
    const [info, setInfo] = useState('');
    const submit = props.submit;
    const place = props.place;

    const handleSubmit = (evt) => {
        evt.preventDefault();

        if (!info) {
            // TODO: inform error
            // return this.setState({ error: 'Password is required' });
            return;
        }

        submit(info, place);
    }

    const handleInfoChange = (evt) => {
        setInfo(evt.target.value);
    }

    return (
        <div className="NewActivity">
        <form onSubmit={handleSubmit}>
            
            <label>Place</label>
            <input type="text" data-test="place" value={place.name} readOnly />

            <label>Info</label>
            <input type="text" data-test="info" value={info} onChange={handleInfoChange} />

            <input type="submit" value="Submit" data-test="submit" />
        </form>
        </div>
    );
}