import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { INACTIVE_ACTIVITY_URL } from '../http-utils';

export function InactiveActivities(props) {
    const [listActivities, setListActivities] = useState([]);
    const [loading, setLoading] = useState(true);
    const approve = props.func.approve;

    useEffect(() => {
        axios.get(INACTIVE_ACTIVITY_URL).then(res => {
            setListActivities(res.data.map((activity, _) => {
                return (
                    <div key={activity.activity_id}>
                        <h4>Activity {activity.activity_id}</h4>
                        {/* TODO: <img src={activity.image.path}/> */}
                        <p>{activity.info}</p>
                        <button onClick={() => approve(activity)}>Approve activity</button>
                        <br/>
                        <hr/>
                    </div>
                );
            }));
            setLoading(false);
        }).catch(err => {
            console.log("Cannot get inactive activities");
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
        {listActivities}
        </div>
    );
    }
