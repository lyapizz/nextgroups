import React, {Component} from 'react';
import {GiVolleyballBall} from "react-icons/gi"
import {MdArrowBack} from "react-icons/md"

import GroupResults from './GroupResults';

class GroupPage extends Component {


    constructor(props, context) {
        super(props, context);
        this.url = props.url;
        this.onBackClicked = props.onBackClicked;
    }

    state = {
        groupResultsResponse: {
            groupResults: []
        }
    }

    componentDidMount() {
        fetch(this.url)
            .then(res => res.json())
            .then((data) => {
                this.setState({groupResultsResponse: data})
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <div className="header-left ">
                    {this.state.groupResultsResponse.tournament != null &&
                    <div className="d-flex flex-column fs-6">
                        <div className="svg-container mb-5" onClick={() => this.onBackClicked()}>
                            <MdArrowBack/>
                            <div className="p-2">
                                <a href="#">Назад к расписанию</a>
                            </div>
                        </div>
                        <div className="svg-container fs-4">
                            <GiVolleyballBall className="text-warning"/>
                            <div className="p-2">
                                {this.state.groupResultsResponse.tournament}
                            </div>
                        </div>
                    </div>
                    }
                </div>
                <GroupResults groupResultsResponse={this.state.groupResultsResponse.groupResults}/>
            </div>
        )
    }
}

export default GroupPage