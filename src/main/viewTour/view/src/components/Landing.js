import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../assets/main.css';
import { NewPlaceButton, ProposedActivitiesButton } from './buttons/LandingButtons';
import { PLACEHOLDER_IMG_URL, BASE_URL, PLACE_URL } from '../http-utils';

function Places(props) {
  const [listPlaces, setListPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const selectPlace = props.selectPlace;

  useEffect(() => {
    axios.get(PLACE_URL).then(res => {
      setListPlaces(res.data.map((place, _) => {
        // TODO: 
        // const listImages = place.images.map((image, _) => {
        //   return (
        //     <img src={image.path} placeholder={PLACEHOLDER_IMG_URL} alt="place image"/>
        //   )
        // });
        return (
<div className="lg:w-1/4">
          <div className="container mx-auto flex flex-wrap items-start my-16" key={place.place_id}>
              <div className="w-full lg:px-3">
                  <div className="bg-gray-200 rounded-xl p-6">
            <h4 className="text-2xl font-bold mb-2" onClick={() => selectPlace(place)}>Place {place.name}</h4>
            {/* TODO: {listImages} */}
            <p className ="text-gray-800 leading-relaxed mb-6">{place.description}</p>
                      <div className="border-t border-gray-300 pt-6 flex items-center justify-between">
                          <span className="text-gray-700">Enjoy</span>
            <button className="text-indigo-500 hover:text-indigo-600 font-medium" onClick={() => selectPlace(place)}>See place</button>
            <br/>
            <hr/>
                      </div>
                      </div>
                  </div>
          </div>
</div>

        );
      }));
      setLoading(false);
    }).catch(err => {
      console.log("Cannot get places");
      console.log(err);
      return (<div>Error</div>);
    });
    
  }, [listPlaces]);

  if (loading) {
    return (
      <div>
        Loading...
      </div>
    );
  }

  return (
    <div>
      {listPlaces}
    </div>
  );
}

export function Landing(props) {
  const isAdmin = props.roles.some(role => role === 'ADMIN');
  const isContributor = props.roles.some(role => role === 'CONTRIBUTOR');
    
  const selectPlace = props.func.selectPlace;

  const addPlace = props.func.addPlace;

  const seeProposedActivities = props.func.seeProposedActivities;

  return(
    <div className="lg:text-center my-16">
      <h1 className="text-center text-gray-700">Click on a place to see its activities.</h1>
      <hr/>
      {isAdmin && (<NewPlaceButton  addPlace={addPlace}/>)}
      {isAdmin && (<ProposedActivitiesButton seeProposedActivities={seeProposedActivities}/>)}
      <hr/>
      
      {/* Print each of places' pictures (carrousel, maybe) and names, linked to the single place profile */}
      <Places selectPlace={selectPlace} isContributor={isContributor}/>
      <br/>
      <hr/>

      
    </div>
  )
}