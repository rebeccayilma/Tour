import React, { useState, useEffect } from 'react';
import { ACTIVITY_URL } from '../http-utils';
import axios from 'axios';

function Activities(props) {
  const [listActivities, setListActivities] = useState([]);
  const selectActivity = props.selectActivity;
  const placeId = props.placeId;

  useEffect(() => {
    axios.get(ACTIVITY_URL).then(res => {
      setListActivities(res.data.map((activity, _) => {
        // TODO: this control MUST be done on backend, not here
        if (activity.place.place_id === placeId) { 
          return (
            <div key={activity.activity_id}>
              <h4>Activity {activity.id}</h4>
              {/* TODO: <img src={activity.image.path}/> */}
              <p>{activity.info}</p>
              <button onClick={() => selectActivity(activity)}>See activity</button>
            </div>
          );
        }
      }));

    }).catch(err => {
      console.log("Cannot get activities of place " + placeId);
      console.log(err);
      return (<div>Error</div>);
    });
    
  }, [listActivities]);

  if (listActivities.length === 0) {
    return (
      <div>
        Loading...
      </div>
    );
  }

  return (
    <div>
      {listActivities}
    </div>
  );
}

export function Place(props) {
  const place = props.place;

  // TODO: 
  // const listImages = place.images.map((image, _) => {
  //   return (
  //     <img src={image.path} placeholder={PLACEHOLDER_IMG_URL} alt="place image"/>
  //   )
  // });

  return(
      <div>
          <div>
            {/* TODO: {listImages} */}
            <h2>{place.name}</h2>
          </div>
          <h3>Click on an activity to see its details</h3>
          <hr/>
          <Activities placeId={place.place_id}/>
      </div>
  )
}