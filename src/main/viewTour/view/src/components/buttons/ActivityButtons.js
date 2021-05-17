export function NewRatingButton(props) {
    return (
        <div>
           <button onClick={props.rate}>Rate activity</button>
        </div>
    );
}

export function DeactivateButton(props) {
    return (
        <div>
           <button onClick={props.deactivate}>Delete activity</button>
        </div>
    );
}

export function SeeRatingsButton(props) {
    return (
        <div>
           <button onClick={props.seeRatings}>Ratings</button>
        </div>
    );
}
