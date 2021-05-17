import React, { useState } from 'react';
import '../../assets/main.css';

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
<div className="p-12 NewPlace">
        <form className="w-full max-w-sm" onSubmit={handleSubmit}>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">
                        <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-full-name">Name</label>
                </div>
                <div className="md:w-2/3">
                    <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" type="text" value="Jane Doe" type="text" data-test="name" value={name} onChange={handleNameChange} />
                </div>
            </div>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">
               <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-latitude">Latitude</label>
                </div>
                <div className="md:w-2/3">
                    <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" type="text" value="Jane Doe" type="text" data-test="name" type="number" data-test="latitude" value={latitude} onChange={handleLatitudeChange} />
                </div>
            </div>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">

                <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-longitude">Longitude</label>
                </div>
                <div className="md:w-2/3">

                <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" type="text" value="Jane Doe" type="text" data-test="name" type="number" data-test="latitude" type="number" data-test="longitude" value={longitude} onChange={handleLongitudeChange} />
                </div>
            </div>
            <div className="md:flex md:items-center mb-6">
                <div className="md:w-1/3">

                <label className="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4" for="inline-description">Description</label>
                </div>
                <div className="md:w-2/3">

                <input className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500" id="inline-full-name" type="text" value="Jane Doe" type="text" data-test="name" type="number" data-test="latitude" type="number" data-test="longitude" type="text" data-test="description" value={description} onChange={handleDescriptionChange} />
                </div>
            </div>
                <div className="md:flex md:items-center">
                    <div className="md:w-1/3"></div>
                    <div className="md:w-2/3">
            <input class="shadow bg-purple-500 hover:bg-purple-400 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded" type="button" type="submit" value="Submit" data-test="submit" />
            </div>
                </div>


        </form>
</div>
    );
}