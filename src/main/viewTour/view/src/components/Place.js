import React, { Component } from 'react';

export class Place extends Component {

  render() {
    return(
        <div>
            <div>{/* Place pictures and name */}</div>
            <h1>Click on an activity to see its details</h1>
            <hr/>
            {/* Get all activities from place */}
            {/* Print activity picture (if null, placeholder) and name, linked to the detail page of the activity */}           
        </div>
    )
  }

}