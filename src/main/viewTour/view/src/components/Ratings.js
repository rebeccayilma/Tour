import React, { useState, useEffect } from 'react';
import { RATINGS_URL } from '../http-utils';
import axios from 'axios';

function Ratings(props) {
    const [listRatings, setListRatings] = useState([]);
    const [loading, setLoading] = useState(true);
  
    const activityId = props.activityId;
  
    useEffect(() => {
      axios.get(RATINGS_URL(activityId)).then(res => {
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
        return (<div>Error</div>);
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
    return (
        <div>
            <h1>Rating list for activity {props.activityId}</h1>
            <hr/>
            <Ratings activityId={props.activityId}/>
        </div>
    )
}
