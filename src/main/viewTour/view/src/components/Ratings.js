import React, { useState, useEffect } from 'react';
import { RATING_URL } from '../http-utils';
import axios from 'axios';

function Ratings(props) {
    const [listRatings, setListRatings] = useState([]);
    const [loading, setLoading] = useState(true);
  
    const activityId = props.activityId;
  
    useEffect(() => {
      axios.get(RATING_URL + '/' + activityId).then(res => {
        setListRatings(res.data.map((rating, _) => {
            return (
              <div key={rating.rating_id}>
                <h5>Date {rating.date}</h5>
                <h5>Score {rating.score}</h5>
                <br/>
                <hr/>
              </div>
            );
        }));
        setLoading(false);
      }).catch(err => {
        console.log("Cannot get ratings of activity " + activityId);
        console.log(err);
        setLoading(false);
        return (<div>No ratings</div>);
      });
      
    }, [loading]);
  
    if (loading) {
      return (
        <div>
          Loading...
        </div>
      );
    }
  
    return (
      <div>
        {listRatings}
      </div>
    );
  }

export function RatingList(props) {
  const activityId = props.activity.activity_id;  
  return (
    <div>
      <h1>Rating list for activity {activityId}</h1>
      <hr/>
      <Ratings activityId={activityId}/>
    </div>
  );
}
