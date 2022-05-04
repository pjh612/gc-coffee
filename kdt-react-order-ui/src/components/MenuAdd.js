import React, {useState} from "react";
import axios from "axios";

export function MenuAdd({onAdd}) {
    const [menu, setMenu] = useState({
        name: "", price: ""
    });
    const handleNameInputChanged = (e) => setMenu({...menu, name: e.target.value})
    const handlePriceInputChanged = (e) => setMenu({...menu, price: e.target.value})


    const onAddMenuSubmit = () => {
            axios.post('http://localhost:8080/api/v1/menus', {
                    name: menu.name,
                    price: menu.price
                }
            ).then(
                v =>{ alert("시술이 추가되었습니다.")
                onAdd(menu,v.data.id);
                },
                e => {
                    alert("서버 장애");
                    console.error(e);
                })
    }
    const handleSubmit = (e) => {
        if (menu.name === "" || menu.price === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onAddMenuSubmit(menu);
        }
    }
    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>시술메뉴 추가 Form</b></h5>
            </div>
            <hr/>
            <form>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">시술 이름</label>
                    <input type="text" className="form-control mb-1" value={menu.name}
                           onChange={handleNameInputChanged}
                           id="name"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">가격 </label>
                    <input type="text" className="form-control mb-1" value={menu.price}
                           onChange={handlePriceInputChanged}
                           id="price"/>
                </div>
            </form>

            <button className="btn btn-dark col-12" onClick={handleSubmit}>시술메뉴 추가</button>


        </>
    )
}