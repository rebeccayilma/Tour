import React, { useState } from 'react';

export function NewPlaceForm(props) {
    const [name, setName] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [description, setDescription] = useState('');
    const submit = props.submit;

    const handleSubmit = (evt) => {
        evt.preventDefault();

        if (!name || !latitude || !longitude || !description) {
            // TODO: inform error
            // return this.setState({ error: 'Field is required' });
            return;
        }

        submit(name, latitude, longitude, description);
    }

    const handleNameChange = (evt) => {
        setName(evt.target.value);
    }

    const handleLatitudeChange = (evt) => {
        setLatitude(evt.target.value);
    }

    const handleLongitudeChange = (evt) => {
        setLongitude(evt.target.value);
    }

    const handleDescriptionChange = (evt) => {
        setDescription(evt.target.value);
    }

    return (
        <div className="NewPlace">
        <form onSubmit={handleSubmit}>
            
            <label>Name</label>
            <input type="text" data-test="name" value={name} onChange={handleNameChange} />

            <label>Latitude</label>
            <input type="number" data-test="latitude" value={latitude} onChange={handleLatitudeChange} />

            <label>Longitude</label>
            <input type="number" data-test="longitude" value={longitude} onChange={handleLongitudeChange} />

            <label>Description</label>
            <input type="text" data-test="description" value={description} onChange={handleDescriptionChange} />

            <input type="submit" value="Submit" data-test="submit" />
        </form>
        </div>
    );
}