export function NewRatingButton(props) {
    return (
        <div className= "p-4">
           <button className="group w-full lg:w-1/3 md:w-2/3 py-2 px-4  border border-transparent text-sm leading-5 font-medium
                rounded-md text-white bg-blue-500 hover:bg-teal-400 focus:outline-none focus:border-teal-400
                focus:shadow-outline-teal active:bg-blue-400 active:outline-none transition duration-150 ease-in-out" onClick={props.rate}>Rate activity</button>
        </div>
    );
}

export function DeactivateButton(props) {
    return (
        <div className= "p-4">
           <button className="group w-full lg:w-1/3 md:w-2/3 py-2 px-4  border border-transparent text-sm leading-5 font-medium
                rounded-md text-white bg-blue-500 hover:bg-teal-400 focus:outline-none focus:border-teal-400
                focus:shadow-outline-teal active:bg-blue-400 active:outline-none transition duration-150 ease-in-out" onClick={props.deactivate}>Delete activity</button>
        </div>
    );
}

export function SeeRatingsButton(props) {
    return (
        <div className= "p-4">
           <button className="group w-full lg:w-1/3 md:w-2/3 py-2 px-4  border border-transparent text-sm leading-5 font-medium
                rounded-md text-white bg-blue-500 hover:bg-teal-400 focus:outline-none focus:border-teal-400
                focus:shadow-outline-teal active:bg-blue-400 active:outline-none transition duration-150 ease-in-out" onClick={props.seeRatings}>Ratings</button>
        </div>
    );
}
