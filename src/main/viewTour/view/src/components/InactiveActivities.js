import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { INACTIVE_ACTIVITY_URL, PLACEHOLDER_IMG_URL } from '../http-utils';
import { useCookies } from 'react-cookie';

export function InactiveActivities(props) {
    const [listActivities, setListActivities] = useState([]);
    const [loading, setLoading] = useState(true);
    const approve = props.func.approve;
    const [cookies, setCookie, removeCookie] = useCookies(['cookie-name']);
  
    const sendJWTConfig = () => {
        return {
            headers: {
                "Authorization": cookies['JWT']
            }
        }
    }

    useEffect(() => {
        axios.get(INACTIVE_ACTIVITY_URL, sendJWTConfig()).then(res => {
            setListActivities(res.data.map((activity, _) => {
                return (
                    <div>
                    <div className=" container mx-auto flex flex-wrap items-start my-16" key={activity.activity_id}>
                        <h4 className="text-2xl font-bold mb-2">Activity {activity.activity_id}</h4>
                    </div>
                <div className=" container mx-auto flex flex-wrap items-start my-16">
                    <img src={activity.imagePath ? activity.imagePath : PLACEHOLDER_IMG_URL} alt="activity"/>
                        <p className="text-2xl font-bold mb-2">{activity.info}</p>
                    </div>
                <div className=" container mx-auto flex flex-wrap items-start my-16">
                        <button className="text-indigo-500 hover:text-indigo-600 font-medium" onClick={() => approve(activity)}>Approve activity</button>
                        <br/>
                        <hr/>

                    </div>
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
