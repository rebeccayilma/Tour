import React, { Component, useEffect, useState } from 'react';
import axios from 'axios';
import { PLACEHOLDER_IMG_URL, BASE_URL } from '../http-utils';

function Places() {
  const [isLoading, setLoading] = useState(true);
  const [listPlaces, setListPlaces] = useState([]);

  const headers = {
    'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb250cmliIiwicm9sZSI6IkNPTlRSSUJVVE9SIiwiZXhwIjoxNjIxMTQ0NTI3fQ.OaYum9rqA4v5YLu4Duvqk3SkAYGcxi6nwMIwC0AIV3JW8sQZY2Qdr7FPL1u-4NiNGwkUDJNpiCWFdP0qp9v9Ug',
    'Access-Control-Allow-Origin': BASE_URL
  };

  useEffect(() => {
    axios.get('api/place', {headers: headers}).then(res => {
      console.log(res.data.map((place, index) => place));
      setListPlaces(res.data.map((place, index) => {
          return (
            <div>
              <h4>Place {place.name}</h4>
              <p>{place.description}</p>
            </div>
          );
        })
      );
      setLoading(false);
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

function NewPlaceButton() {
  return (
    <div>
      {/* TODO: Button for adding new place */}
      <a href={this.addPlace()}>Add place</a>
    </div>
  );
}

function ProposedActivitiesButton() {
  return (
    <div>
      {/* TODO: Button for adding new place */}
      <a href={this.seeProposedActivities()}>See proposed activities</a>
    </div>
  );
}

export function Landing(props) {
  const [isAdmin, setAdmin] = useState(props.roles.some(role => role === 'admin'));
  const [isContributor, setContributor] = useState(props.roles.some(role => role === 'contributor'));
    
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

      {/* If Admin, "Add new place" button */}
      {isAdmin && (<NewPlaceButton addPlace={addPlace}/>)}
      {/* If Admin, "See proposed activities" button */}
      {isAdmin && (<ProposedActivitiesButton seeProposedActivities={seeProposedActivities}/>)}
      
    </div>
  )
}