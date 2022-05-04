import React from "react";
import axios from "axios";

export function Customer(props) {
    const id = props.id;
    const name = props.name;
    const email = props.email;
    const gender = props.gender;
    const birth = props.birth;

    const handleSubmit = (e) => {
        axios({
                url:`http://localhost:8080/api/v1/customers/`+id,
                method : 'delete'
            }

        ).then(
            v =>{ alert("손님 정보가 삭제되었습니다.")
            props.onRemove(id)
            },
            e => {
                alert("서버 장애");
                console.error(e);
            })
    }

    return <>
        <div className="col">
            <div className="row text-muted">{id}</div>
        </div>
        <div className="col text-center name">{name}</div>
        <div className="col text-center email">{email}</div>
        <div className="col text-center gender">{gender}</div>
        <div className="col text-center birth">{birth}</div>
        <button className="btn btn-small btn-outline-dark" onClick={handleSubmit}>삭제</button>
    </>
}