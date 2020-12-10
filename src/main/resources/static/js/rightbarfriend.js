function loadUserFriend() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/api/getuserfriend/",
        success: function (list) {
            let content = '';
            let length;
            if (list.length < 5) {
                length = list.length
            } else {
                length = 4
            }
            for (let i = 0; i < length; i++) {
                content += fillFriendTable(list[i]);
            }
            document.getElementById('listUserFriend').innerHTML = '';
            document.getElementById('listUserFriend').innerHTML = content;
        }
    })
}

function fillFriendTable(friend) {
return  `<tr class="spaceUnder">
            <td><img src="../${friend.getAvatarURL()}" alt="" width="30px" height="30px" style="border-radius: 50%">
            </td>
            <td style="padding-left: 2px"><a href="#"><span style="font-size: 13px">${friend.getUserName()}</span></a>
            </td>
            <td style="padding-left: 5px">
                <button style="font-size: xx-small">Unfriend</button>
            </td>
        </tr>`
}