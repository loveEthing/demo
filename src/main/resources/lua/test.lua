local mylist = redis.call("lrange",KEYS[1],0,-1)
local count = 0
for i,k in ipairs(mylist) do
    redis.call("incr",k)
    count = count + 1
end
return count