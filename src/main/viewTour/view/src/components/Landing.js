import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { NewPlaceButton, ProposedActivitiesButton } from './buttons/LandingButtons';
import { PLACEHOLDER_IMG_URL, BASE_URL, PLACE_URL } from '../http-utils';

function Places(props) {
  const [listPlaces, setListPlaces] = useState([]);
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
          <div key={place.place_id}>
            {/* TODO: add selectPlace(place) without automatic redirection */}
            <h4>Place {place.name}</h4>
            {/* TODO: {listImages} */}
            <p>{place.description}</p>
            <button onClick={() => selectPlace(place)}>See place</button>
          </div>
        );
      }));

    }).catch(err => {
      console.log("Cannot get places");
      console.log(err);
      return (<div>Error</div>);
    });
    
  }, [listPlaces]);

  if (listPlaces.length === 0) {
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
  // const isContributor = props.roles.some(role => role === 'CONTRIBUTOR');
    
  const selectPlace = props.func.selectPlace;

  const addPlace = props.func.addPlace;

  const seeProposedActivities = props.func.seeProposedActivities;

  return(
    <div>
      <h1>Click on a place to see its activities.</h1>
      <hr/>
      
      {/* Print each of places' pictures (carrousel, maybe) and names, linked to the single place profile */}
      <Places selectPlace={selectPlace} />
      <br/>

      {isAdmin && (<NewPlaceButton addPlace={addPlace}/>)}
      {isAdmin && (<ProposedActivitiesButton seeProposedActivities={seeProposedActivities}/>)}
      
    </div>
  )
}